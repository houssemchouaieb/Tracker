<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if (isset($_POST['username'])and isset($_POST['password'])){

			$db =new DbOperations();
			$result=$db->adminLogin(
				$_POST['username'],
				$_POST['password']);
			if($result==1){
				$response['error']=false;
				$response['message']="Admin connected successfully";
			}
			else if($result==0){
				$response['error']=true;
				$response['message']="Username or password incorrect";
			}

		}else {
			$response['error']=true;
			$response['message']="Required fields are missing";
		}


	}else{
		$response['error']=true;
		$resopnse['message']="Invalid Request";
	}

	echo json_encode($response);