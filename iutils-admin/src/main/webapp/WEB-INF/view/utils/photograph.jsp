<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/view/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>拍照工具</title>
</head>
<body>
	<!-- 拍照弹出层 -->
	<div id="showPhotograph">
		<div style="width: 344px;height: 244px;;">
			<div id="webcam" style="text-align: center"></div>
			<canvas id="canvas" height="240" width="320" style="position:absolute;top:8px;display:none;"></canvas>
	  	</div>
		<div class="text-center" style="margin-top: 5px;text-align: center">
	         <button type="button" onclick="remake()">重拍</button>
	         <button type="button" onclick="javascript:webcam.capture();savePhotograph();">拍照</button>
	         <button type="button" id="saveImg" onclick="saveImg('${ctx}')" style="display:none;">保存</button>
	     </div>
	</div>

	<script src="${ctxStatic}/custom/js/jquery-1.11.0.min.js"></script>
	<script src="${ctxStatic}/custom/js/jquery.webcam.min.js"></script>
	<script type="text/javascript">
		var pos = 0;
		var ctx = null;
		var cam = null;
		var image = null;

		var filter_on = false;
		var filter_id = 0;
		//保存图像
		function savePhotograph() {
			$("#canvas").show();
			$("#saveImg").show();
			if (filter_on) {
				filter_id = (filter_id + 1) & 7;
			}
		}
		//重拍
		function remake() {
			$("#canvas").hide();
			$("#saveImg").hide();
		}
		//保存图像到后台
		function saveImg() {
			var imgSrc = $("#canvas")[0].toDataURL("image/png");
			$
					.ajax({
						url : "${ctx}/uploadFileToBase64",
						data : {
							data : imgSrc.replace("data:image/png;base64,", "")
						},
						type : "post",
						dataType : 'json',
						success : function(data) {
							if (data.ret == 1) {
							 	window.parent.saveImgBack(data.data);
							} else {
								layer.alert(data.msg);
							}
						},
						error : function() {
							layer.alert("上传异常");
						}
					});
		}
		
		window.addEventListener("load", function() {
			var canvas = document.getElementById("canvas");
			if (canvas.getContext) {
				ctx = document.getElementById("canvas").getContext("2d");
				ctx.clearRect(0, 0, 320, 240);
				var img = new Image();
				img.src = "";
				img.onload = function() {
					ctx.drawImage(img, 129, 89);
				}
				image = ctx.getImageData(0, 0, 320, 240);
			}
		}, false);

		$("#webcam").webcam(
				{
					width : 320,
					height : 240,
					mode : "callback",
					swffile : "${ctxStatic}/custom/js/jscam_canvas_only.swf",
					onTick : function(remain) {
						if (0 == remain) {
							$("#status").text("Cheese!");
						} else {
							$("#status").text(
									remain + " seconds remaining...");
						}
					},
					onSave : function(data) {
						var col = data.split(";");
						var img = image;
						if (false == filter_on) {
							for ( var i = 0; i < 320; i++) {
								var tmp = parseInt(col[i]);
								img.data[pos + 0] = (tmp >> 16) & 0xff;
								img.data[pos + 1] = (tmp >> 8) & 0xff;
								img.data[pos + 2] = tmp & 0xff;
								img.data[pos + 3] = 0xff;
								pos += 4;
							}
						} else {

							var id = filter_id;
							var r, g, b;
							var r1 = Math.floor(Math.random() * 255);
							var r2 = Math.floor(Math.random() * 255);
							var r3 = Math.floor(Math.random() * 255);

							for ( var i = 0; i < 320; i++) {
								var tmp = parseInt(col[i]);

								if (id == 0) {
									r = (tmp >> 16) & 0xff;
									g = 0xff;
									b = 0xff;
								} else if (id == 1) {
									r = 0xff;
									g = (tmp >> 8) & 0xff;
									b = 0xff;
								} else if (id == 2) {
									r = 0xff;
									g = 0xff;
									b = tmp & 0xff;
								} else if (id == 3) {
									r = 0xff ^ ((tmp >> 16) & 0xff);
									g = 0xff ^ ((tmp >> 8) & 0xff);
									b = 0xff ^ (tmp & 0xff);
								} else if (id == 4) {

									r = (tmp >> 16) & 0xff;
									g = (tmp >> 8) & 0xff;
									b = tmp & 0xff;
									var v = Math.min(
											Math
													.floor(.35 + 13 * (r
															+ g + b) / 60),
											255);
									r = v;
									g = v;
									b = v;
								} else if (id == 5) {
									r = (tmp >> 16) & 0xff;
									g = (tmp >> 8) & 0xff;
									b = tmp & 0xff;
									if ((r += 32) < 0)
										r = 0;
									if ((g += 32) < 0)
										g = 0;
									if ((b += 32) < 0)
										b = 0;
								} else if (id == 6) {
									r = (tmp >> 16) & 0xff;
									g = (tmp >> 8) & 0xff;
									b = tmp & 0xff;
									if ((r -= 32) < 0)
										r = 0;
									if ((g -= 32) < 0)
										g = 0;
									if ((b -= 32) < 0)
										b = 0;
								} else if (id == 7) {
									r = (tmp >> 16) & 0xff;
									g = (tmp >> 8) & 0xff;
									b = tmp & 0xff;
									r = Math.floor(r / 255 * r1);
									g = Math.floor(g / 255 * r2);
									b = Math.floor(b / 255 * r3);
								}

								img.data[pos + 0] = r;
								img.data[pos + 1] = g;
								img.data[pos + 2] = b;
								img.data[pos + 3] = 0xff;
								pos += 4;
							}
						}

						if (pos >= 0x4B000) {
							ctx.putImageData(img, 0, 0);
							pos = 0;
						}
					},
					onCapture : function() {
						webcam.save();
					},
					debug : function(type, string) {
						$("#status").html(type + ": " + string);
					},
					onLoad : function() {
						var cams = webcam.getCameraList();
						for ( var i in cams) {
							$("#cams").append("<li>" + cams[i] + "</li>");
						}
					}
				});

	</script>
</body>
</html>