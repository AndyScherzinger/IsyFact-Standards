package de.bund.bva.pliscommon.bridges.integration.config;

import de.bund.bva.isyfact.aufrufkontext.AufrufKontext;
import de.bund.bva.isyfact.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.isyfact.aufrufkontext.impl.AufrufKontextFactoryImpl;
import de.bund.bva.isyfact.aufrufkontext.stub.AufrufKontextVerwalterStub;
import de.bund.bva.isyfact.serviceapi.service.httpinvoker.v1_0_0.bridge.util.ServiceApiMapper;
import de.bund.bva.pliscommon.bridges.integration.sst.PlisTestRemoteBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class TestClientConfiguration {

    //aus Isyfact 2.x
    @Bean
    public AufrufKontextVerwalter<AufrufKontext> isyAufrufKontextVerwalter() {
        AufrufKontextVerwalterStub<AufrufKontext> verwalterStub = new AufrufKontextVerwalterStub<>();
        verwalterStub.setAufrufKontextFactory(new AufrufKontextFactoryImpl<>());
        return verwalterStub;
    }

    //aus der Bridge
    @Bean
    public ServiceApiMapper serviceApiMapper() {
        return new ServiceApiMapper();
    }

    //Definition des Proxy auf Clientseite
    @Bean
    public HttpInvokerProxyFactoryBean invoker(@Value("${server.port}") final int port) {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:" + port + "/TestService");
        invoker.setServiceInterface(PlisTestRemoteBean.class);
        return invoker;
    }

}
