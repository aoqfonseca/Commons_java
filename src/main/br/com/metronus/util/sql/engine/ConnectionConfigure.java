package br.com.neoris.util.sql.engine;


/**
 * @author Andre Fonseca
 * 
 * Classe que encapsula os dados de configuração de uma conexão
 */
public class ConnectionConfigure {

    private String url;
    private String user;
    private String password;
    private String driverClass;
    private String dataSource;    
    private int tipo;    
    private int poolSize;
    private String name;
    private boolean showQuery = false;    
    public static final int DATASOURCE = 0;
    public static final int CONNECTION = 1;
    public static final int POOL_CONNECTION = 2;    
    
    
    
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
  
    
    /**
     * @return Returns the showQuery.
     */
    public boolean isShowQuery() {
        return showQuery;
    }
    /**
     * @param showQuery The showQuery to set.
     */
    public void setShowQuery(boolean showQuery) {
        this.showQuery = showQuery;
    }
    /**
     * @return Returns the poolSize.
     */
    public int getPoolSize() {
        return poolSize;
    }
    /**
     * @param poolSize The poolSize to set.
     */
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
    /**
     * @return Returns the tipo.
     */
    public  int getTipo() {
        return tipo;
    }
    /**
     * @param tipo The tipo to set.
     */
    public  void setTipo(String tipoNome) {
        if (tipoNome.equalsIgnoreCase("driver")) {
            this.tipo = ConnectionConfigure.CONNECTION;
        } else if (tipoNome.equalsIgnoreCase("pool_drive")) {
            this.tipo =  ConnectionConfigure.POOL_CONNECTION;
        } else if (tipoNome.equalsIgnoreCase("datasource")) {
            this.tipo =  ConnectionConfigure.DATASOURCE;
        } else {            
            throw new NullPointerException("Nenhum tipo conhecido foi definido. Os tipos são: driver, pool_driver, datasource");
        }
    }
    /**
     * @param tipo The tipo to set.
     */
    public  void setTipo(int tipo) {
        this.tipo = tipo;
    }
    /**
     * @return Returns the dataSource.
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource
     *            The dataSource to set.
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return Returns the driverClass.
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass
     *            The driverClass to set.
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return Returns the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            The user to set.
     */
    public void setUser(String user) {
        this.user = user;
    }
}