package org.apache.shiro.session.mgt.quartz;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义会话验证器
 * 重写 SessionValidationJob
 * @author iutils.cn
 */
public class QuartzSessionValidationJob implements Job {

    /**
     * Key used to store the session manager in the job data map for this job.
     */
    public static final String SESSION_MANAGER_KEY = "sessionManager";

    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);

        if (log.isDebugEnabled()) {
            log.debug("Executing session validation Quartz job...");
        }

        sessionManager.validateSessions();

        if (log.isDebugEnabled()) {
            log.debug("Session validation Quartz job complete.");
        }
    }
}
