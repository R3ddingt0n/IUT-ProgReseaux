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
#define BACKLOG 50
#define NB_CLIENTS 100

using namespace std;

void exitErreur(const char * msg) {
	perror(msg);
	exit( EXIT_FAILURE);

}

int main() {

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

	char * msg;
	time_t date;

	cout << "Serveur DayTime lancé  sur le port " << NUM_PORT << endl;

	for (int i = 1; i <= NB_CLIENTS; i++) {

		struct sockaddr_in sockaddr_client;
		socklen_t addrlen_client = sizeof(sockaddr_client);

		sock_client = accept(sock_serveur, (struct sockaddr *)&sockaddr_client, &addrlen_client);
		if (sock_client == -1)
			exitErreur("accept");

		cout << "Un client d'adresse IP " << inet_ntoa(sockaddr_client.sin_addr) << " s'est connecté sur son port " << sockaddr_client.sin_port << " (méthode accept)" <<endl;

		addrlen_client = sizeof(sockaddr_client);
		if(getpeername(sock_client, (struct sockaddr*)&sockaddr_client, &addrlen_client) == -1)
			exitErreur("getpeername");

		cout << "Un client d'adresse IP " << inet_ntoa(sockaddr_client.sin_addr) << " s'est connecté sur son port " << sockaddr_client.sin_port << " (méthode getpeername)" <<endl;

		date = time(NULL);
		msg = ctime(&date);

		if (write(sock_client, msg, strlen(msg)) == -1)
			exitErreur("write");

		close(sock_client);

	}
	close(sock_serveur);
	return 0;
}
