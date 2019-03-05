#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define NUM_PORT 50013
#define IP_ADDRESS "10.203.9.212"
#define BUFFER_SIZE 1024

using namespace std;

void exitErreur(const char * msg) {
    perror(msg);
    exit( EXIT_FAILURE);
    
}//exitErreur

int main(int argc, char* argv[]) {
    
    if(argc != 3){
        cerr << "Usage : ./client-tcp-messagerie.run <IP@> <port>" << endl;
        return -1;
    }
    
    /*
    cout << "IP = " << argv[1] << endl
         << "PORT = " << argv[2] << endl;
    */
    
    char sendBuffer[BUFFER_SIZE];
    char recvBuffer[BUFFER_SIZE];
    
    int sock_client = socket(AF_INET, SOCK_STREAM, 0);
    struct sockaddr_in sockaddr_client, sockaddr_serveur;
    
    sockaddr_serveur.sin_family = AF_INET;
    sockaddr_serveur.sin_port = htons(atoi(argv[2]));
    inet_aton(argv[1], &sockaddr_serveur.sin_addr);
    
    if(connect(sock_client, (struct sockaddr*)&sockaddr_serveur, sizeof(sockaddr_serveur)) == -1)
        exitErreur("connect");
    
    
    cout << "Connecté à " << argv[1] << ":" << argv[2] << endl << endl;
    
    while (true)
    {
        cout << "Client> ";
        cin.getline(sendBuffer, BUFFER_SIZE);
        
        if (send(sock_client, sendBuffer, sizeof(sendBuffer), 0) < 0)
        {
            exitErreur("send");
        }
        
        if (strcmp("bye", sendBuffer) == 0)
        {
            cout << endl << "Déconnexion" << endl;
            
            break;
        }
        
        if (recv(sock_client, recvBuffer, sizeof(recvBuffer), 0) < 0)
        {
            exitErreur("recv");
        }
        
        cout << "Server> " << recvBuffer << endl;
    }
    close(sock_client);
    
}//main
