# moum.bangdpool.com.conf
server {
    server_name moum.bangdpool.com;

    location / {
        proxy_pass https://127.0.0.1:8080; # 8080번 포트로 전달
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    client_max_body_size 100M;

    # WebSocket 요청 처리
    location /ws {
        proxy_pass https://127.0.0.1:8080;
        proxy_http_version 1.1;                     # WebSocket에 필요한 HTTP 버전
        proxy_set_header Upgrade $http_upgrade;     # WebSocket 업그레이드
        proxy_set_header Connection "upgrade";      # WebSocket 연결 유지
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/moum.bangdpool.com/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/moum.bangdpool.com/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}

server {
    listen 80;
    server_name moum.bangdpool.com;
    #return 301 https://$host:443$request_uri; # HTTP -> HTTPS 리다이렉트

    location / {
        proxy_pass https://127.0.0.1:8080;
    }


    client_max_body_size 100M;

    # WebSocket 요청 처리
    location /ws {
        proxy_pass https://127.0.0.1:8080;
        proxy_http_version 1.1;                     # WebSocket에 필요한 HTTP 버전
        proxy_set_header Upgrade $http_upgrade;     # WebSocket 업그레이드
        proxy_set_header Connection "upgrade";      # WebSocket 연결 유지
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}



# wiki.moum.bangdpool.com.conf
server {
    server_name wiki.moum.bangdpool.com;

    location / {
        proxy_pass http://127.0.0.1:3000; # 3000번 포트로 전달
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/wiki.moum.bangdpool.com/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/wiki.moum.bangdpool.com/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}

server {
    listen 80;
    server_name wiki.moum.bangdpool.com;
    return 301 https://$host:443$request_uri; # HTTP -> HTTPS 리다이렉트
}

