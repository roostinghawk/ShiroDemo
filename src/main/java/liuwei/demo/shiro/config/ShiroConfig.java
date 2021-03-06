package liuwei.demo.shiro.config;

import liuwei.demo.shiro.consts.Const;
import liuwei.demo.shiro.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

	/**
	 * 全局
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//默认跳转到登陆页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登陆成功后的页面
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		//自定义过滤器
		Map<String,Filter> filterMap=new LinkedHashMap<>();
		shiroFilterFactoryBean.setFilters(filterMap);
		//权限控制map
		Map<String,String> filterChainDefinitionMap=new LinkedHashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/403", "anon");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 核心：SecurityManager
	 */
	@Bean
	public SecurityManager securityManager(DefaultWebSessionManager sessionManager){
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager ();
		//设置realm
		securityManager.setRealm( myRealm()  );
		securityManager.setRememberMeManager(rememberMeManager());
		securityManager.setCacheManager( ehCacheManager() );
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}


	/**
	 * 身份认证Realm
	 */
	@Bean
	public MyRealm myRealm(){
		MyRealm myRealm = new MyRealm();
		myRealm.setCredentialsMatcher(  hashedCredentialsMatcher() );
		return myRealm;
	}

	/**
	 * 哈希密码比较器。在myShiroRealm中作用参数使用
	 * 登陆时会比较用户输入的密码，跟数据库密码配合盐值salt解密后是否一致。
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName(Const.HASH_ALGORITHM);
		//散列的次数，比如散列两次，相当于 md5( md5(""));
		hashedCredentialsMatcher.setHashIterations(Const.HASH_INTERATIONS);
		return hashedCredentialsMatcher;
	}


	@Bean(name = "sessionDao")
	public EnterpriseCacheSessionDAO sessionDao(){
		EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
		sessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
		return sessionDao;
	}

	/**
	 * Session管理Bean
	 */
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager sessionManager(EnterpriseCacheSessionDAO sessionDAO) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setGlobalSessionTimeout(86400000);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationInterval(1800000);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		Cookie cookie = new SimpleCookie("ad.es.session.id");
		cookie.setHttpOnly(Boolean.FALSE);
		cookie.setPath("/");
		sessionManager.setSessionIdCookie(cookie);
		sessionManager.setSessionIdCookieEnabled(true);
		return sessionManager;
	}

	/**
	 *  缓存：使用Ehcache
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager(){
		EhCacheManager cacheManager=new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return cacheManager;
	}

	/**
	 * RememberMe
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥  默认AES算法
//		cookieRememberMeManager.setCipherKey();
		return  cookieRememberMeManager;
	}

	/**
	 * cookie对象
	 * @return
	 */
	@Bean
	public Cookie rememberMeCookie() {
		SimpleCookie simpleCookie=new SimpleCookie("rememberMe");
		//记住我cookie生效时间，单位秒
		simpleCookie.setMaxAge(3600);
		return simpleCookie;
	}


	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * Shiro生命周期处理器
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 自动创建代理
	 * @return
	 */
	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
}
