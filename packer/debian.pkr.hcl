packer {
  required_plugins {
    amazon = {
      source  = "github.com/hashicorp/amazon"
      version = ">= 1.0.0"
    }
  }
}

variable "aws_region" {
  type    = string
  default = "us-east-1"
}

variable "source_ami" {
  type    = string
  default = "ami-06db4d78cb1d3bbf9"
}

variable "ssh_username" {
  type    = string
  default = "admin"
}

variable "source_file" {
  type    = string
  default = ""
}

variable "accounts_file" {
  type    = string
  default = ""
}
variable "ami_users" {
  type    = list(string)
  default = ["969159499630", "196011838237"]
}

variable "instance_type" {
  type    = string
  default = "t2.micro"
}

# https://www.packer.io/plugins/builders/amazon/ebs
source "amazon-ebs" "my-ami" {
  region          = var.aws_region
  ami_name        = "csye6225_${formatdate("YYYY_MM_DD_hh_mm_ss", timestamp())}"
  ami_description = "AMI for CSYE 6225"
  ami_regions     = [var.aws_region]
  ami_users       = var.ami_users

  instance_type = var.instance_type
  source_ami    = var.source_ami
  ssh_username  = var.ssh_username

  launch_block_device_mappings {
    delete_on_termination = true
    device_name           = "/dev/xvda"
    volume_size           = 8
    volume_type           = "gp2"
  }
}


build {
  sources = ["source.amazon-ebs.my-ami"]

  provisioner "shell" {
    environment_vars = [
      "DEBIAN_FRONTEND=noninteractive",
      "CHECKPOINT_DISABLE=1"
    ]

    script = "install-script.sh"
  }



  provisioner "file" {

    source      = "${var.source_file}"
    destination = "/home/admin/"
  }
  provisioner "file" {
    source      = "${var.accounts_file}"
    destination = "/home/admin/"
  }
}

