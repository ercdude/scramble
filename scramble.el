;;; scramble-docker.el --- Helper commands for scramble dev -*- lexical-binding: t -*-
;;
;; URL:
;; Author: Eric Menezes <eirc.m@protonmail.com>

(defvar compose-command-fmt
  "ssh %s 'docker-compose -f %s --project-directory %s %s'"
  "The format of the docker-compose command")

(defvar compose-build-command-fmt
  "ssh %s 'docker-compose -f %s --project-directory %s build'"
  "The format of the docker-compose command")

(defvar scramble-docker-host "ice"
  "The host which docker is running.")

(defvar scramble-project-path "/imported/scramble"
  "The path of project file.")

(defvar scramble-compose-path (concat scramble-project-path
                                      "/scramble.compose")
  "The path of docker-compose file.")

(defvar scramble-dev-service "scramble_clojure-dev_1"
  "The name of the development container.")

(defvar scramble-container-exec-command (list "docker" "container" "exec")
  "A list as argv for executing something inside a container.")

(defvar scramble-start-repl-command "lein with-profile +socket repl"
  "The command for starting repl.")

(defvar scramble-run-command "lein run"
  "The command to start the scramble application")

;;;###autoload
(defun scramble-compose-up ()
  "Starts the composition of scramble"
  (interactive)
  (let ((command (format compose-command-fmt
                         scramble-docker-host
                         scramble-compose-path
                         scramble-project-path
                         "up -d")))
    (compile command)))

;;;###autoload
(defun scramble-compose-down ()
  "Starts the composition of scramble"
  (interactive)
  (let ((command (format compose-command-fmt
                         scramble-docker-host
                         scramble-compose-path
                         scramble-project-path
                         "down")))
    (compile command)))

;;;###autoload
(defun scramble-compose-build ()
  "Starts the composition of scramble"
  (interactive)
  (let ((command (format compose-build-command-fmt
                         scramble-docker-host
                         scramble-compose-path
                         scramble-project-path)))
    (compile command)))

(defun scramble-make-comint (container command &optional path)
  "Makes a comint buffer with COMMAND inside the given docker CONTAINER."
  (let ((default-directory (format "/ssh:%s:"
                                   scramble-docker-host))
        (program (car scramble-container-exec-command))
        (args (cdr scramble-container-exec-command))
        (work-dir (or path "/src")))
    (apply 'make-comint
           "scramble-dev"
           program
           nil
           `(,@args "-itw" ,work-dir ,container ,command))))

(defun scramble-run-command (container command)
  "Runs a COMMAND inside the given docker CONTAINER from PATH dir.."
  (let ((default-directory (format "/ssh:%s:"
                                   scramble-docker-host)))
    (compile (string-join `(,@scramble-container-exec-command
                            "-it"
                            ,container
                            ,command)
                          " "))))

;;;###autoload
(defun scramble-start-repl ()
  "Starts repl in the docker container."
  (interactive)
  (scramble-make-comint scramble-dev-service
                        scramble-start-repl-command))

;;;###autoload
(defun scramble-dev-shell ()
  "Starts the composition of scramble"
  (interactive)
  (scramble-make-comint scramble-dev-service "/bin/sh"))
