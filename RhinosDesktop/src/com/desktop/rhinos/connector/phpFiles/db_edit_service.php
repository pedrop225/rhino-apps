<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Services
								SET state='".$_REQUEST['state']."',
								notes='".$_REQUEST['notes']."',
								referencia='".$_REQUEST['referencia']."',
								f_pago='".$_REQUEST['f_pago']."',
								p_neta='".$_REQUEST['p_neta']."',
								ccc='".$_REQUEST['ccc']."',
								cartera='".$_REQUEST['cartera']."',
								anualizar='".$_REQUEST['anualizar']."'
								WHERE (id='".$_REQUEST['id']."')");
	mysql_close();
?>