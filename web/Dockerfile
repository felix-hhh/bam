FROM nginx:1.27.1-alpine

COPY ./dist/ /usr/share/nginx/html/
COPY ./default.conf /etc/nginx/conf.d/

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

EXPOSE 80