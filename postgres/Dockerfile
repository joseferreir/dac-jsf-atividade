FROM postgres
MAINTAINER Ricardo Jose <joseifpb2015@gmail.com>
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 123456
ENV POSTGRES_DB atv_jsf
COPY tabelas.sql /docker-entrypoint-initdb.d/
#postgres-volume-db:/var/lib/postgresql/data/ 
