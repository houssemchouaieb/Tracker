<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['username']) ){

			$db =new DbOperations();
			$result=$db-> userLogout(
				$_POST['username']
			);
			$response['message']="User Logged out successfully";
			$response['error']=false;
			
	}}
	echo json_encode($response);