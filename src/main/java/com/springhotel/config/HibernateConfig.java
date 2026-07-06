package com.springhotel.config;


import java.util.Properties;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuracion de Hibernate y la base de datos de SpringHotelApp.
 *
 * <p>Esta clase es el equivalente a {@code hibernate.cfg.xml} combinado 
 * con la configuracion del datasource. Aqui se define lo necesario 
 * para que la aplicacion pueda conectarse a la base de datos y usar 
 * Hibernate como ORM.</p>
 *
 * <p>Un ORM (Object-Relational Mapping) es una herramienta que nos permite
 * trabajar con la base de datos usando objetos Java en lugar de escribir
 * SQL a mano.</p>
 *
 * <p>Anotaciones que lleva esta clase:</p>
 * <ul>
 *   <li>{@code @Configuration} - indica a Spring que esta clase contiene
 *       definiciones de beans, como si fuera un XML de configuracion.</li>
 *   <li>{@code @EnableTransactionManagement} - activa el soporte de
 *       transacciones con {@code @Transactional}. Una transaccion agrupa
 *       varias operaciones en la base de datos: si una falla, todas se
 *       deshacen (rollback).</li>
 *   <li>{@code @PropertySource} - carga el archivo {@code database.properties}
 *       del classpath para leer las credenciales de la base de datos.</li>
 * </ul>
 *
 * @author  Ana Laura y Manuel
 * @version 1.0
 * @see AppInitializer
 * @see SpringMvcConfig
 */
//IA: (Claude y Gemini) utilizada para consulta, optimizacion, comprobacion y correcion de algunos errores codigo. 
//IA: javadoc generado con ayuda de IA(Claude)
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class HibernateConfig {
	
    /**
     * Objeto de Spring que da acceso a todas las propiedades cargadas
     * en la aplicación, incluyendo las del archivo {@code database.properties}.
     *
     * <p>Se inyecta automáticamente mediante el constructor, siguiendo el
     * mismo patrón que el resto de la aplicación.</p>
     */
    private final Environment env;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param env objeto Environment proporcionado por Spring para acceder
     *            a las propiedades de configuración.
     */
    @Autowired
    public HibernateConfig(Environment env) {
        this.env = env;
    }
	/**
     * Crea y configura el DataSource de la aplicacion.
     *
     * <p>El DataSource es el objeto que gestiona la conexion fisica con la
     * base de datos. Es el punto de entrada: sin el, ni Hibernate ni Spring
     * pueden hablar con la base de datos.</p>
     *
     * <p>Se usa {@link DriverManagerDataSource}, una implementacion de Spring.</p>
     *
     * <p>Los valores se leen del archivo {@code database.properties}:</p>
     * <ul>
     *   <li>{@code db.driver} - clase del driver JDBC.</li>
     *   <li>{@code db.url} - URL de conexion.</li>
     *   <li>{@code db.username} - usuario de la base de datos.</li>
     *   <li>{@code db.password} - contrasena de la base de datos.</li>
     * </ul>
     *
     * @return instancia de {@link DataSource} lista para ser usada por Hibernate.
     */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(env.getRequiredProperty("db.driver"));
		ds.setUrl(env.getRequiredProperty("db.url"));
		ds.setUsername(env.getRequiredProperty("db.username"));
		ds.setPassword(env.getRequiredProperty("db.password"));
		return ds;
	}
	
	/**
     * Crea y configura la SessionFactory de Hibernate.
     *
     * <p>La {@link SessionFactory} es el objeto central de Hibernate.
     * Se crea una sola vez al arrancar la aplicacion y se reutiliza.
     * A partir de ella se obtienen las {@code Session}, que son las que 
     * ejecutan las consultas y los cambios en la base de datos.</p>
     *
     * <p>Se usa {@link LocalSessionFactoryBean}, que es la forma que tiene
     * Spring de crear una {@code SessionFactory} integrada con su
     * contenedor de beans.</p>
     *
     * <p>Configuracion que se aplica:</p>
     * <ul>
     *   <li><b>DataSource</b> - le pasamos la conexion a la base de datos
     *       definida en el metodo {@link #dataSource()}.</li>
     *   <li><b>packagesToScan</b> - le decimos a Hibernate donde buscar
     *       las clases anotadas con {@code @Entity} (nuestros modelos).
     *       Escanea el paquete {@code com.springhotel.model}.</li>
     *   <li><b>hibernate.dialect</b> - le indica a Hibernate que "dialecto"
     *       de SQL debe generar. Cada base de datos tiene sus propias
     *       particularidades; con {@code MySQL8Dialect} genera SQL compatible
     *       con MySQL 8.</li>
     *   <li><b>hibernate.show_sql / format_sql</b> - muestra en consola el
     *       SQL que genera Hibernate, formateado y legible. Muy util para
     *       depurar y aprender que consultas se estan lanzando.</li>
     * </ul>
     *
     * @return instancia de {@link LocalSessionFactoryBean} configurada.
     * @throws Exception si hay algun problema al inicializar la SessionFactory.
     */
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws Exception {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(dataSource());
		sf.setPackagesToScan("com.springhotel.entity");
		
		Properties props = new Properties();
		props.setProperty("hibernate.dialect",      "org.hibernate.dialect.MySQL8Dialect");
        props.setProperty("hibernate.show_sql",     "true");
        props.setProperty("hibernate.format_sql",   "true");
        sf.setHibernateProperties(props);
		
		return sf;
	}
	
	 /**
     * Crea el gestor de transacciones de la aplicacion.
     *
     * <p>Una transaccion es un conjunto de operaciones en la base de datos
     * que se tratan como una unidad. Si todas tienen exito, se confirman
     * (commit). Si alguna falla, todas se deshacen (rollback), dejando
     * la base de datos en su estado anterior.</p>
     *
     * <p>Spring gestiona las transacciones automaticamente gracias a la
     * anotacion {@code @Transactional} en los metodos de servicio. Para
     * que eso funcione necesita este bean, que le dice como hablar con
     * Hibernate para abrir, confirmar o deshacer transacciones.</p>
     *
     * <p>Spring inyecta automaticamente la {@link SessionFactory} creada
     * en {@link #sessionFactory()} gracias al parametro del metodo.</p>
     *
     * @param sessionFactory la SessionFactory de Hibernate, inyectada
     *                       automaticamente por Spring.
     * @return instancia de {@link HibernateTransactionManager} lista para usar.
     */
	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
		
	}
}
