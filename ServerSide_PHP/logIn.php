<?php
$servername = "localhost";
$username = "root";
$password = "";

// Create connection
$conn = new mysqli($servername, $username, $password);
$db = new mysqli('localhost', 'root', '', 'network2');

// Check connection
if ($conn->connect_error) {
die("Connection failed: " . $conn->connect_error);
}

if (isset( $_REQUEST['ID']) && isset( $_REQUEST['pass'])) {
    $id = $_REQUEST['ID'];
    $pass = $_REQUEST['pass'];
    $id_ex = 0;
    $sql = "SELECT id, password,admin FROM network2.employee";
    $result = $conn->query($sql);
    if ($result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            if ($row["id"] == $id){
                if($row["password"] == $pass){
                    if($row["admin"] == 0)
                        echo "0";
                    else if($row["admin"] == 1) echo "1";
                } else echo "Password Wrong!";
                $id_ex = 1;
            }
        }
        if ($id_ex == 0)
            echo "There is no user with this ID!";
    }
}
?>