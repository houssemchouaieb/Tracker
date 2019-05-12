<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if (isset($_POST['username'])and isset($_POST['password'])and isset($_POST['numcar'])){

			$db =new DbOperations();
			$result=$db->userLogin(
				$_POST['username'],
				$_POST['password'],
				$_POST['numcar']);
			if($result==1){
				$response['error']=false;
				$response['message']="User connected successfully";
				$user=$db->getUserByUserName($_POST['username']);
				$response['id']=$user['id'];
				$response['username']=$user['username'];
				$response['email']=$user['email'];
			}
			else if($result==0){
				$response['error']=true;
				$response['message']="Username or password incorrect";
			}
			else if($result==2){
				$response['error']=true;
				$response['message']="Bus is already occupied";
			}
			else if($result==3){
				$response['error']=true;
				$response['message']="Bus does not exist";
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
