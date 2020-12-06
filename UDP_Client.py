import socket
import time

server_host = 'localhost'		# Endereco IP do Servidor
server_port = 8022				# Porta que o servidor vai escutar
server_dest = (server_host, server_port)

print 'Bem vindo ao trabalho UDP !!!'
print 'Para sair use CTRL+X\n'
msg = ''
option = ''

while option <> '\x18':
	
	while True:
	
		print '-------------------------------'
		print 'Escolha uma opcao'
		print '-------------------------------'
		print ' 1 - Enviar um inteiro'
		print ' 2 - Enviar um caractere'
		print ' 3 - Uma cadadeia de caracteres'
		print ' CTRL + c - Para sair do progrma'
		print '-------------------------------'

		option = raw_input(); #capturando a opcao
	
		print '-------------------------------'
		
		if(option == '1' or option == '2' or option =='3'):
			break
		else:
			print("Erro: Digite a opcao correta!")
			
	print "Digite a mensagem"
	
	try:
		msg = raw_input()
		msg = option + msg
	except:
		print("Erro ao capturar mensagem, por favor digite novamente")
	else:
		#inicio da contagem RTT
		inicial_time = time.time()
		udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
		udp.sendto (msg, server_dest)
	
	print '------------------------------'
	#recebendo mensagem do servidor
	print 'Recebendo mensagem do servidor'
	try:
		msg, server = udp.recvfrom(1024)
		#termino da contagem do RTT
		ending_time = time.time()
		print server, msg
		print('RTT: ' + str(ending_time - inicial_time) + ' segundos')

	except:
		print("Erro ao receber a mensagem do servidor, tenten enviar a mensagem novamente")
	

udp.close()
