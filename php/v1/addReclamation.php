<?php
	
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['name']) and isset($_POST['date'])and isset($_POST['description']) ){

			$db =new DbOperations();
			$result=$db-> createRec(
				$_POST['name'],
				$_POST['date'],
				$_POST['description']
			);
			
			$response['error']=false;
			$response['message']="Reclamation was sended successfully ";
		}}
		else{
			$response['error']=true;
			$resopnse['message']="Invalid Request";
		}	
	echo json_encode($response);