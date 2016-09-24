<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

	//Insertando datos en tabla Login
	$q = $db->prepare("	INSERT INTO Login (user, password) 
						VALUES (:user, :password)");
	
	$q->bindParam(':user', $_REQUEST['user']);
	$q->bindParam(':password', $_REQUEST['password']);
	$q->execute();

	//Comprobando el id adjudicado
	$q = $db->prepare("SELECT id FROM Login
						WHERE user = :user");
	
	$q->bindParam(':user', $_REQUEST['user']);
	$q->execute();
	
	$id = $q->fetch(PDO::FETCH_ASSOC)['id'];
	
	//Insertando en tabla Usuarios con el id anterior
	$q = $db->prepare("	INSERT INTO Users (id, name, mail)
						VALUES (:id, :name, :mail)");
	
	$q->bindParam(':id', $id);
	$q->bindParam(':name', $_REQUEST['name']);
	$q->bindParam(':mail', $_REQUEST['mail']);
	$q->execute();
								
	$q = $db->prepare(" INSERT INTO Structure (parent, child, p_profit)
						VALUES (1, $id, 0)");
	$q->execute();
?>