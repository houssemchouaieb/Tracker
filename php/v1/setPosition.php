<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['lati']) and isset($_POST['longi'])and isset($_POST['username']) ){

			$db =new DbOperations();
			$result=$db-> setPosition(
				$_POST['lati'],
				$_POST['longi'],
				$_POST['username']
			);
		}
	}