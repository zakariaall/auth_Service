# 1. Utiliser une image légère dial Java 21
FROM eclipse-temurin:21-jre-alpine

# 2. Dossier fin ghadi i-koun l-code dakhla l-container
WORKDIR /app

# 3. Copier l-JAR li t-generat f stage 'Build Artifact'
# T-aked belli smiya matchi dakchi li f target/
COPY target/*.jar app.jar

# 4. L-port li k-i-khdem fih l-Auth Service dialk
EXPOSE 8082

# 5. L-amr bach n-lanciwo l-app
ENTRYPOINT ["java", "-jar", "app.jar"]