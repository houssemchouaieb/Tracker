<?php
	require_once '../includes/DbOperations.php';

	$response =array();

	if($_SERVER['REQUEST_METHOD']=='POST'){

		if(isset($_POST['numcar']) ){

			$db =new DbOperations();
			$result=$db-> getPosition(
				$_POST['numcar']
			);
			$verif=$db->isBusExist($_POST['numcar']);
			if($verif){
				$verif2=$db->isBusFree($_POST['numcar']);
				if($verif2){
					$response['error']=true;
					$response['message']="Bus is out of service";
				}
				else{
					$response['error']=false;
					$response['lati']=$result['lati'];
					$response['longi']=$result['longi'];
				}
				
			}else{
				$response['error']=true;
				$response['message']="Bus does not exist";
			}

		}
		else {
			$response['error']=true;
			$response['message']="Please enter the number of bus";
		}
	}
		echo json_encode($response);