<?php
	
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['numero']) and isset($_POST['ligne']) ){

			$db =new DbOperations();
			$result=$db-> addBus(
				$_POST['numero'],
				$_POST['ligne']
			);
			if($result==1){
				$response['error']=false;
				$response['message']="Bus added successfully ";
			}
			else if($result==2){
				$response['error']=true;
				$response['message']="Some error occurred please try again";
			}
			else if($result==0){
				$response['error']=false;
				$response['message']="Bus already exists Try again";
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