<?php
	error_reporting(E_ALL);
	ini_set('display_errors', 1);
	class DbOperations{
		private $con;
		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php'; 

			$db =new DbConnect();

			$this->con=$db->connect();

		}

		public function createUser($username,$pass,$email,$telephone){
			if($this->isUserExist($username)){
				return 0;
			}else{

			$password=md5($pass);
			$stmt=$this->con->prepare("INSERT INTO `users` (`id`, `username`, `email`, `password`,`numcar`,`lati`,`longi`,`telephone`) VALUES (NULL, ?, ?,?,'empty',0.0,0.0,?)");

		
			$stmt->bind_param("ssss",$username,$email,$password,$telephone);

			if($stmt->execute()){
				return 1;
			}
			else{
				return 2;
			}

		}}

		public function userLogin($username,$pass,$numcar){
			if($this->isBusExist($numcar)){
				if($this->isBusFree($numcar)){
					$password=md5($pass);
					$stmt=$this->con->prepare("SELECT id FROM users WHERE username=? AND password=?");
					$stmt->bind_param("ss",$username,$password);
					$stmt->execute();
					$stmt->store_result();
					if($stmt->num_rows>0){
						$this->changeBusState($numcar,TRUE);
						$this->changeUserBus($username,$numcar);
						return 1;
					}
					else{
						return 0;
					}
				}
				else{
					return 2;
				}
			}
			else{
				return 3;
			}
		}
		public function adminLogin($username,$pass){
			$password=md5($pass);
			$stmt=$this->con->prepare("SELECT id FROM admin WHERE username=? AND password=?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;
		}

		

		public function getUserByUserName($username){
			$stmt=$this->con->prepare("SELECT * FROM users WHERE username=?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}

		public function isUserExist($username){
			$stmt=$this->con->prepare("SELECT id FROM users WHERE username=?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;
		}
		public function setPosition($lati,$longi,$username){
			$stmt=$this->con->prepare("UPDATE `users` SET `lati`=?,`longi`=? WHERE username=?");
			$stmt->bind_param("sss",$lati,$longi,$username);
			$stmt->execute();
			
		}
		public function getPosition($numcar){
			$stmt=$this->con->prepare("SELECT * FROM `users` WHERE `numcar`=?");
			$stmt->bind_param("s",$numcar);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
			
		}
		public function deleteUser($username){
			if($this->isUserExist($username)){
				$stmt=$this->con->prepare("DELETE FROM `users` WHERE username=?");
				$stmt->bind_param("s",$username);
				$stmt->execute();
				return 1;
			}
			else{
				return 0;
			}
		}
		public function isBusExist($numero){
			$stmt=$this->con->prepare("SELECT id FROM bus WHERE numero=?");
			$stmt->bind_param("s",$numero);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;
		}
		public function isBusFree($numero){
			$stmt=$this->con->prepare("SELECT id FROM bus WHERE numero=? AND working=false");
			$stmt->bind_param("s",$numero);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;
		}
		public function isUserFree($username){
			$stmt=$this->con->prepare("SELECT id FROM users WHERE username=? AND numcar='empty'");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows>0;
		}
		public function addBus($numero,$ligne){
			if($this->isBusExist($numero)){
				return 0;
			}else{

			$stmt=$this->con->prepare("INSERT INTO `bus` (`id`, `numero`, `working`, `ligne`) VALUES (NULL, ?, false,?)");

		
			$stmt->bind_param("ss",$numero,$ligne);

			if($stmt->execute()){
				return 1;
			}
			else{
				return 2;
			}

		}
		}
		private function changeBusState($numcar,$state){
			$stmt=$this->con->prepare("UPDATE `bus` SET `working`=? WHERE numero=?");
			$stmt->bind_param("ss",$state,$numcar);
			$stmt->execute();	
		}
		private function changeUserBus($username,$newBus){
			$stmt=$this->con->prepare("UPDATE `users` SET `numcar`=? WHERE username=?");
			$stmt->bind_param("ss",$newBus,$username);
			$stmt->execute();
		}
		public function deleteBus($numero){
			if($this->isBusExist($numero)){
				$stmt=$this->con->prepare("DELETE FROM `bus` WHERE numero=?");
				$stmt->bind_param("s",$numero);
				$stmt->execute();
				return 1;
			}
			else{
				return 0;
			}
		}

		public function userLogout($username){
			$stmt=$this->con->prepare("SELECT `numcar` FROM `users` WHERE username=?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			$result=$stmt->get_result()->fetch_assoc();
			$bus=$result['numcar'];
			if($bus!='empty'){
				$this->changeBusState($bus,FALSE);
			}
			$stmt=$this->con->prepare("UPDATE `users` SET `numcar`='empty',`lati`='0.0',`longi`='0.0' WHERE username=?");
			$stmt->bind_param("s",$username);
			$stmt->execute();

		}
		public function createRec($name,$date,$description){
			

			
			$stmt=$this->con->prepare("INSERT INTO `reclamation` (`name`, `date`, `description`,`id`) VALUES (?, ?,?,NULL)");

		
			$stmt->bind_param("sss",$name,$date,$description);

			$stmt->execute();

		}

	}	