FROM azul/zulu-openjdk:21

RUN apt-get update && \
    apt-get install -y \
        xvfb \
        tightvncserver \
		xfce4 \
		xfce4-terminal \
		x11-xserver-utils \
        mc \
		lsof \
        net-tools \
    && rm -rf /var/lib/apt/lists/*

# Create X11 socket dir with proper permissions
RUN mkdir -p /tmp/.X11-unix && \
    chmod 1777 /tmp/.X11-unix
	
# Create non-root user
RUN useradd -m dockeruser

USER dockeruser
WORKDIR /home/dockeruser

COPY target/websocket-capture-screen-1.0.0-SNAPSHOT.jar ./application.jar

ENV USER=dockeruser
ENV DISPLAY=:1

RUN mkdir -p ~/.vnc && \
    echo "secret" | vncpasswd -f > ~/.vnc/passwd && \
    chmod 600 ~/.vnc/passwd

EXPOSE 5901

CMD bash -c "\
    rm -f /tmp/.X11-unix/X1 & \
    tightvncserver :1 -geometry 1920x1080 -depth 24 && \
	java -jar application.jar"