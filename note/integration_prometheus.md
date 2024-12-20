### Add dependencies to file pom.xml
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### In folder deployment
1. Create folder:
    ```yaml
    mkdir data/prometheus_data
    ```
2. Grant permission:
    ```yaml
    chmod -R 777 ./data/
    ```
3. Create file prometheus/prometheus.yml
    ```yaml
    global:
      scrape_interval: 15s
    
    scrape_configs:
      - job_name: "prometheus"
        scrape_interval: 5s
        static_configs:
          - targets: ["host.docker.internal:9090"]
        metrics_path: "/metrics"
    
      - job_name: "springboot-application"
        scrape_interval: 5s
        static_configs:
          - targets: [ "host.docker.internal:8080" ]
        metrics_path: "/actuator/prometheus"
    ```

4. Add config in application.yml
    ```yaml
    management:
      endpoints:
        web:
          exposure:
            include:
              - "*"
      endpoint:
        health:
          show-details: always
        prometheus:
          enabled: true
    ```
5. Run command docker
    ```dockerfile
    docker run -d \
      --name prometheus \
      --restart unless-stopped \
      -p 9090:9090 \
      -v ./deployment/config/prometheus/:/etc/prometheus/ \
      -v ./deployment/data/prometheus_data:/prometheus \
      --add-host host.docker.internal:host-gateway \
      prom/prometheus:latest \
      --config.file=/etc/prometheus/prometheus.yml \
      --storage.tsdb.path=/prometheus \
      --web.console.libraries=/etc/prometheus/console_libraries \
      --web.console.templates=/etc/prometheus/consoles \
      --web.enable-lifecycle
    ```
6. Access http://localhost:8080/actuator/prometheus to check result
7. Install grafana
   ```yaml
   docker run -d \
     --name grafana \
     --hostname grafana \
     -p 3000:3000 \
     -v ./deployment/data/grafana_data:/var/lib/grafana \
     -v ./deployment/config/grafana/resolv.conf:/etc/resolv.conf \
     --add-host host.docker.internal:host-gateway \
     -e GF_SECURITY_ADMIN_PASSWORD=admin \
     -e GF_USERS_ALLOW_SING_UP=false \
     -e GF_SERVER_DOMAIN=localhost \
     -e GF_LOG_MODE="console file" \
     -e GF_LOG_FILTERS="alerting.notifier.slack:debug alertmanager:debug ngalert:debug" \
     grafana/grafana:latest
   ```
8. Access http://localhost:3000 to add prometheus datasource
9. Import dashboard JVM (Micrometer)
10. Install node exporter
   ```yaml
   docker run -d \
     --name node_exporter \
     --restart unless-stopped \
     -p 9100:9100 \
     -v /proc:/host/proc:ro \
     -v /sys:/host/sys:ro \
     -v /:/rootfs:ro \
     prom/node-exporter:latest \
     --path.procfs=/host/proc \
     --path.rootfs=/rootfs \
     --path.sysfs=/host/sys \
     --collector.filesystem.mount-points-exclude="^/(sys|proc|dev|host|etc)($$|/)"
   ```

11. Add config to prometheus.yml
   ```yaml
     - job_name: "node-exporter"
       scrape_interval: 5s
       static_configs:
         - targets: [ "host.docker.internal:9100" ]
   ```

12. Restart all container