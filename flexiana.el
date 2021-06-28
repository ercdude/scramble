;;; flexiana-docker.el --- Helper commands for flexiana dev -*- lexical-binding: t -*-
;;
;; URL:
;; Author: Eric Menezes <eirc.m@protonmail.com>

(defvar compose-command-fmt
  "ssh %s 'docker-compose -f %s --project-directory %s %s'"
  "The format of the docker-compose command")

(defvar compose-build-command-fmt
  "ssh %s 'docker-compose -f %s --project-directory %s build'"
  "The format of the docker-compose command")

(defvar flexiana-docker-host "ice"
  "The host which docker is running.")

(defvar flexiana-project-path "~/cm/recipes/flexiana/"
  "The path of project file.")

(defvar flexiana-compose-path (concat flexiana-project-path
                                     "/flexiana.compose")
  "The path of docker-compose file.")

(defvar flexiana-dev-service "flexiana_clojure-dev_1"
  "The name of the development container.")

(defvar flexiana-container-exec-command (list "docker" "container" "exec")
  "A list as argv for executing something inside a container.")

;;;###autoload
(defun flexiana-compose-up ()
  "Starts the composition of flexiana"
  (interactive)
  (let ((command (format compose-command-fmt
                         flexiana-docker-host
                         flexiana-compose-path
                         flexiana-project-path
                         "up -d")))
    (compile command)))

;;;###autoload
(defun flexiana-compose-down ()
  "Starts the composition of flexiana"
  (interactive)
  (let ((command (format compose-command-fmt
                         flexiana-docker-host
                         flexiana-compose-path
                         flexiana-project-path
                         "down")))
    (compile command)))

(defun flexiana-make-comint (container command)
  "Makes a comint buffer with COMMAND inside the given docker CONTAINER."
  (let ((default-directory (format "/ssh:%s:"
                                   flexiana-docker-host))
        (program (car flexiana-container-exec-command))
        (args (cdr flexiana-container-exec-command)))
    (apply 'make-comint
           "flexiana-dev"
           program
           nil
           `(,@args "-it" ,container ,command))))

(defun flexiana-run-command (container command)
  "Runs a COMMAND inside the given docker CONTAINER."
  (let ((default-directory (format "/ssh:%s:"
                                   flexiana-docker-host)))
    (compile (string-join `(,@flexiana-container-exec-command
                            "-it"
                            ,container
                            ,command)
                          " "))))

;;;###autoload
(defun flexiana-dev-shell ()
  "Starts the composition of flexiana"
  (interactive)
  (flexiana-make-comint flexiana-dev-service "/bin/sh"))
