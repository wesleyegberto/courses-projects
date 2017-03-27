# instalação de um comando
exec {
	"apt-update": # apelido usado para referencias
		command => "/usr/bin/apt-get update"
}

# instalação de um pacote
package {
	"unzip":
		ensure => installed, # Puppet lançará erro se não conseguir instalar
		require => Exec["apt-update"] # Depende do update
}

# instalações de vários pacotes
package {
	["openjdk-7-jre", "tomcat7", "mysql-server"]:
		ensure => installed,
		require => Exec["apt-update"]
}

# cria um serviço
service {
	"tomcat7":
		ensure => running, #puppet para se não tiver rodando
		enable => true, #habilita o serviço
		hasstatus => true, #informa que o serviço tem um status (catalina status)
		hasrestart => true, #informa que o serviço consegue restart
		require => Package["tomcat7"] #dependência
}

service {
	"mysql":
		ensure => running,
		enable => true,
		hasstatus => true,
		hasrestart => true,
		require => Package["mysql-server"]
}

# cria o banco de dados
exec {
	"createdb_musicjungle":
		command => "mysqladmin -uroot create musicjungle",
		unless => "mysql -u root musicjungle", # se for true não executa o exec (skip)
		path => "/usr/bin", # caminho do executável
		require => Service["mysql"]
}

# cria um usuário no BD
exec {
	"createuser_musicjungle":
		command => "mysql -uroot -e \"GRANT ALL PRIVILEGES ON * TO 'musicjungle'@'%' IDENTIFIED BY 'minha-senha';\" musicjungle",
		unless => "mysql -umusicjungle -pminha-senha musicjungle", # executa se o user já existir
		path => "/usr/bin",
		require => Exec["createdb_musicjungle"]
}

# copia um arquivo dentro da VM
file {
	"/var/lib/tomcat7/webapps/vraptor-musicjungle.war": #caminho completo de destino na VM
		source => "/vagrant/manifests/vraptor-musicjungle.war", #caminho completo de origem na VM
		owner => tomcat7, #define o dono (Puppet roda como root e este é o padrão)
		group => tomcat7, #define o grupo
		mode => 0644, #permissões do arquivo
		require => Package["tomcat7"], #dependência
		notify => Service["tomcat7"] #notifca o serviço para dar restart
}

# cria uma função que append linha a um arquivo
# usando as keys da tarefa
define appendLine($file, $line) {
		exec { "/bin/echo '${line}' >> '${file}'":
				unless => "/bin/grep -qFx '${line}' '${file}'"
		}
}

# usando a função definida anteriormente
appendLine {
	"production":
		file => "/etc/default/tomcat7",
		line => "JAVA_OPTS=\"\$JAVA_OPTS -Dbr.com.caelum.vraptor.environment=production\"",
		require => Package["tomcat7"],
		notify => Service["tomcat7"]
}