
locals {
  region_eu = "europe-west3-a"
  p_name = "my-small-gcp-project"
}


provider "google" {
 credentials = file("auth.json")
 project     = local.p_name
 region      = local.region_eu
}


// Terraform plugin for creating random ids
resource "random_id" "instance_id" {
 byte_length = 8
}

// A single Google Cloud Engine instance
resource "google_compute_instance" "default" {
 count = 1
 name         = "build-machine${random_id.instance_id.hex}"
 machine_type = "e2-medium"
 zone         = local.region_eu

 boot_disk {
   initialize_params {
     image = "ubuntu-1804-bionic-v20200129a"
   }
 }
 metadata = {
   ssh-keys = "kuba:${file("~/.ssh/id_rsa.pub")}"
 }

  // Make sure flask is installed on all new instances for later steps
 metadata_startup_script = "sudo apt-get update; sudo apt-get upgrade -y; "

 network_interface {
   network = "default"
   access_config {
     // Include this section to give the VM an external ip address
   }
 }
}

resource "google_compute_firewall" "default" {
 name    = "app-firewall"
 network = "default"

 allow {
   protocol = "tcp"
   ports    = ["8081"]
 }
}

// A variable for extracting the external ip of the instance
output "m1" {
 value = "${google_compute_instance.default.0.network_interface.0.access_config.0.nat_ip}"
}


