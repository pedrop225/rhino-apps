<?php
	
	class Server {
		
		private $pdo;
		
		//constructor
		public function Server() {
			
			include 'db_settings.php';
				
			$this->pdo = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
			$this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		}
		
		public function login() {
			
			$q = $this->pdo->prepare('SELECT * FROM Clients');
			$q->execute();
				
			return $this->create_json($q);
		}
		
		private function create_json($q) {
			
			while ($e = $q->fetch(PDO::FETCH_ASSOC))
				$result[] = $e;
			
			return json_encode($result);
		}
	}
	
	$server = new SoapServer(null, array('uri'=>'http://localhost/services/'));
	$server->setClass("Server");
	$server->handle();
?>