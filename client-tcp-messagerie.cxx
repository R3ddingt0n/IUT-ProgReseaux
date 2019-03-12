#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <mesFonctions.h>

#define NUM_PORT 50013
#define IP_ADDRESS "10.203.9.148"
#define BUFFER_SIZE 1024

using namespace std;

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

        string message = sendBuffer;
        message += '\n';

        if (write(sock_client, message.c_str(), message.size()) < 0)
        {
            exitErreur("write");
        }

        if (strcmp("bye", sendBuffer) == 0)
        {
            cout << endl << "Déconnexion" << endl;
            break;
        }

	int line = readLine(sock_client, message);
        if (line == -1)
        {
            exitErreur("read");
        }
	if(line == 0){
		break;
	}

        cout << "Server> " << message;
    }
    close(sock_client);

}//main

