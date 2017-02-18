package cc.cicadabear.security.domain.oauth;

import cc.cicadabear.security.domain.shared.Repository;

import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author Shengzhao Li
 */
public interface OauthRepository extends Repository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}