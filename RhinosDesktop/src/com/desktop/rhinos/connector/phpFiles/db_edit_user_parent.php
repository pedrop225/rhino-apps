<?php
	include 'db_settings.php';
	
	$db = new PDO("mysql:host=$mysql_host;dbname=$mysql_database;charset=utf8mb4", $mysql_user, $mysql_password);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
	$q = $db->prepare("	UPDATE Structure
						SET parent = :parent', p_profit = :p_profit
							WHERE (child = :child)");
	
	$q->setParam(':parent', $_REQUEST['parent']);
	$q->setParam(':p_profit', $_REQUEST['p_profit']);
	$q->setParam(':child', $_REQUEST['child']);
	$q->execute();
?>