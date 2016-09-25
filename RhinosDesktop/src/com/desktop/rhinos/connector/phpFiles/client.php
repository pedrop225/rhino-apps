<?php
	
	$client = new SoapClient(null, 	array(	'location'=>'http://localhost/services/server.php',
			'uri'=>'http://localhost/services/'));
	
	echo $client->login();
?>