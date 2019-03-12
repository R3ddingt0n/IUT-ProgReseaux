#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

#define NUM_PORT 50013
#define IP_ADDRESS "10.203.9.148"
#define BACKLOG 50
#define BUFFER_SIZE 1024

using namespace std;

void exitErreur(const char * msg) {
    perror(msg);
    exit( EXIT_FAILURE);

}

int main() {

    char sendBuffer[BUFFER_SIZE];
    char recvBuffer[BUFFER_SIZE];
    int sock_serveur = socket(AF_INET, SOCK_STREAM, 0);

    struct sockaddr_in sockaddr_serveur;

    sockaddr_serveur.sin_family = AF_INET;
    sockaddr_serveur.sin_port = htons(NUM_PORT);
    //sockaddr_serveur.sin_addr.s_addr = htonl(INADDR_ANY);
    inet_aton(IP_ADDRESS, &sockaddr_serveur.sin_addr);

    int yes = 1;
    if (setsockopt(sock_serveur, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int))
            == -1)
        exitErreur("setsockopt");

    if (bind(sock_serveur, (struct sockaddr *) &sockaddr_serveur,
             sizeof(sockaddr_in)) == -1)
        exitErreur("bind");

    if (listen(sock_serveur, BACKLOG) == -1)
        exitErreur("listen");

    int sock_client;

    while(true){
        struct sockaddr_in sockaddr_client;
        socklen_t addrlen_client;

        sock_client = accept(sock_serveur, (struct sockaddr *)&sockaddr_client, &addrlen_client);
        if (sock_client == -1)
            exitErreur("accept");

        cout << "Connexion de " << inet_ntoa(sockaddr_client.sin_addr) << ":" << sockaddr_client.sin_port << endl;

        while (true)
        {
            if (read(sock_client, recvBuffer, sizeof(recvBuffer)) < 0)
            {
                exitErreur("recv");
            }

            cout << "Client> " << recvBuffer;
            cout << endl;

            if (strcmp("bye", recvBuffer) == 0)
            {
                cout << "Déconnexion de " << inet_ntoa(sockaddr_client.sin_addr) << ":" << ntohs(sockaddr_client.sin_port) << endl << endl;
                break;
            }

            cout << "Server> ";
            cin.getline(sendBuffer, BUFFER_SIZE);

            if (write(sock_client, sendBuffer, sizeof(sendBuffer)) < 0)
            {
                exitErreur("send");
            }
        }
        close(sock_serveur);
        return 0;
    }
}
