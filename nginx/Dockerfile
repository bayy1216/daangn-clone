FROM nginx
# 사용자 정의한 Nginx 구성 파일을 컨테이너 내부로 복사합니다.
COPY ./nginx.conf /etc/nginx/nginx.conf

COPY ./service-url.inc /etc/nginx/conf.d/service-url.inc
# 포트 80을 외부에 노출합니다.
EXPOSE 80
# 컨테이너가 시작될 때 Nginx를 실행합니다.
CMD ["nginx", "-g", "daemon off;"]