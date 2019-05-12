<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['username']) ){

			$db =new DbOperations();
			
			$verif=$db->isUserFree($_POST['username']);
			if($verif){
				$result=$db-> deleteUser(
				$_POST['username']
				);
				if($result==1){
					$response['error']=false;
					$response['message']="User Deleted successfully";

				}
			}
			else{
				$verif2=$db->isUserExist($_POST['username']);
				if($verif2==0){
					$response['error']=false;
					$response['message']="User doesn't exist";
				}
				else{
					$response['error']=false;
					$response['message']="User is working you cannot delete it";
				}
				
			}
		}
		else {
			$response['error']=true;
			$response['message']="Required fields are missing ";
		}
	}
	echo json_encode($response);