package kz.epam.spadv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

import javax.jws.WebService;

/**
 * Created by olegm on 16.03.2016.
 */
@Configuration
@EnableWs
public class WsConfig extends WsConfigurerAdapter {
}
