#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <mesFonctions.h>

#define BUFFER_SIZE 1024

using namespace std;

int main(int argc, char* argv[]) {
    
    if(argc != 3){
        cerr << "Usage : ./client-udp-echo.run <IP@> <port>" << endl;
        return -1;
    }
    
    /*
    cout << "IP = " << argv[1] << endl
         << "PORT = " << argv[2] << endl;
    */
    
    int sock_client = socket(AF_INET, SOCK_DGRAM, 0);
    struct sockaddr_in sockaddr_client, sockaddr_serveur;
    
    sockaddr_serveur.sin_family = AF_INET;
    sockaddr_serveur.sin_port = htons(atoi(argv[2]));
    inet_aton(argv[1], &sockaddr_serveur.sin_addr);
    
    char buf[BUFFER_SIZE];
    string message;
    
    cout << " ->";
    getline(cin, message);
    
    if (sendto(sock_client, message.c_str(), message.size(), 0, (struct sockaddr *) &sockaddr_serveur, sizeof(sockaddr_serveur)) == -1)
        exitErreur("sendto");
    
    cout << "Réponse du serveur :" << endl;
    socklen_t size = sizeof(sockaddr_client);
    int nbOctetsRecus = recvfrom(sock_client, buf, sizeof(buf), 0, (struct sockaddr *) &sockaddr_client, &size);
    if (nbOctetsRecus == -1)
        exitErreur("recvfrom");
    
    cout << string(buf,nbOctetsRecus) << endl;
    
    close(sock_client);
    
}//main
