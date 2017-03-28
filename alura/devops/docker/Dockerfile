# define a imagem base
FROM ubuntu

MAINTAINER wesleyegberto

# permite rodar comandos que usamos no docker run/xec
RUN apt-get update && apt-get install -y apache2

# cria uma variável de ambiente
ENV WEBDIR /var/www/html/

# adiciona os arquivos da pasta app na pasta html
# será acessado através do /index.html
ADD app/ ${WEBDIR}html

# copia a pasta app para a pasta app no container
# será acessado atráves do /app/index.html
COPY app/ ${WEBDIR}/app

# informa à engine que o container escuta na porta em runtime
EXPOSE 80

# define o comando que rodará o processo quando o container iniciar (para não precisar passar no run)
# comando abaixo: /usr/sbin/apache2ctl -D FOREGROUND
CMD ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]