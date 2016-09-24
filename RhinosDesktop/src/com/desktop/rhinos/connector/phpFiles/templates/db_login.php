<?php
	include 'db_settings.php';
	
	$user = $_REQUEST['user'];
	
	$db = new PDO('mysql:host=localhost;dbname=backup;charset=utf8mb4', 'root', '');
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("SELECT * FROM Login NATURAL JOIN Users WHERE user=:user");
	$q->bindParam(':user', $user);
	
	$q->execute();
	
	while ($e = $q->fetch()) 
		$output[] = $e;
	
	print(json_encode($output));
?>