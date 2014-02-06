package com.randomcompany.security;

import java.lang.reflect.Constructor;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import oracle.adf.share.common.ClassUtils;

public class MyInitialContextFactory
  implements InitialContextFactory
{
  public static final String ENV_JAAS_CONTEXT = "oracle.adf.security.context";
  public static final String JAAS_CONTEXT = "oracle.adf.share.security.providers.jazn.JAZNSecurityContext";
  public static final String DEFAULT_CONTEXT = "oracle.adf.share.security.SecurityContextImpl";
  public static final String XS_CONTEXT = "oracle.adf.share.security.providers.jps.xds.XsSecurityContext";
  static final String JAZN_LOGINMODULE = "oracle.security.jazn.realm.RealmLoginModule";
  
  public Context getInitialContext(Map env)
    throws NamingException
  {
    Context ctx = null;
    String contextName = env != null ? (String)env.get("oracle.adf.security.context") : null;
    if (contextName == null) {
      contextName = "oracle.adf.share.security.providers.jps.JpsSecurityContext";
    }
    try
    {
      Class cls = ClassUtils.forName(contextName);
      try
      {
        ctx = (Context)cls.getConstructor(new Class[] { Map.class }).newInstance(new Object[] { env });
      }
      catch (NoSuchMethodException e)
      {
        if ((env instanceof Hashtable)) {
          ctx = (Context)cls.getConstructor(new Class[] { Hashtable.class }).newInstance(new Object[] { (Hashtable)env });
        } else {
          ctx = (Context)cls.getConstructor(new Class[] { Hashtable.class }).newInstance(new Object[] { new Hashtable(env) });
        }
      }
    }
    catch (Exception e)
    {
      if (!contextName.equals("oracle.adf.share.security.SecurityContextImpl")) {
        try
        {
          Class cls = ClassUtils.forName("oracle.adf.share.security.SecurityContextImpl");
          ctx = (Context)cls.getConstructor(new Class[] { Map.class }).newInstance(new Object[] { env });
        }
        catch (Exception e2)
        {
          NamingException ne = new NamingException(e2.getMessage());
          ne.setRootCause(e2);
          throw ne;
        }
      }
    }
    return ctx;
  }
  
  /**
   * @deprecated
   */
  public Context getInitialContext(Hashtable env)
    throws NamingException
  {
    return getInitialContext(env);
  }
}
