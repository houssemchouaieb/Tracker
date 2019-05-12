<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['numero']) ){

			$db =new DbOperations();
			
			$verif=$db->isBusFree($_POST['numero']);
			if($verif){
				$result=$db-> deleteBus(
				$_POST['numero']
				);
				if($result==1){
					$response['error']=false;
					$response['message']="Bus Deleted successfully";

				}
			}
			else{
				$verif2=$db->isBusExist($_POST['numero']);
				if($verif2==0){
					$response['error']=false;
					$response['message']="Bus doesn't exist";
				}
				else{
					$response['error']=false;
					$response['message']="Bus is working you cannot delete it";
				}
				
			}
		}
		else {
			$response['error']=true;
			$response['message']="Required fields are missing ";
		}
	}
	echo json_encode($response);