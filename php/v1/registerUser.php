<?php
	
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['username']) and isset($_POST['email'])and isset($_POST['password'])and isset($_POST['telephone']) ){

			$db =new DbOperations();
			$result=$db-> createUser(
				$_POST['username'],
				$_POST['password'],
				$_POST['email'],
				$_POST['telephone']
				
			);
			if($result==1){
				$response['error']=false;
				$response['message']="User registred successfully ";
			}
			else if($result==2){
				$response['error']=true;
				$response['message']="Some error occurred please try again";
			}
			else if($result==0){
				$response['error']=false;
				$response['message']="User already exists";
			}


		}
		else{
			$response['error']=true;
			$response['message']="Required fields are missing";
		}


	}
	else{
		$response['error']=true;
		$resopnse['message']="Invalid Request";
	}	
	echo json_encode($response);