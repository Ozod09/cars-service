package uz.taxi.cars_service.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.SSLException;
import java.time.Duration;

@Configuration
public class WebClientConfig implements WebFluxConfigurer {

    @Bean("webClient")
    public WebClient webClient() throws SSLException {

        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        ConnectionProvider provider = ConnectionProvider.builder("provider")
                .maxConnections(1000) // default 500 max connections
                .pendingAcquireMaxCount(500) // default 500 pending acquire max count
                .pendingAcquireTimeout(Duration.ofSeconds(45)) // default 45s pending acquire timeout
                .maxIdleTime(Duration.ofSeconds(60)) // default 60s max idle time
                .maxLifeTime(Duration.ofMinutes(10)) // default 10m max lifetime
                .build();

        HttpClient httpClient = HttpClient.create(provider).secure(t -> t.sslContext(sslContext));

        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();

        return WebClient
                .builder()
                .exchangeStrategies(strategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
