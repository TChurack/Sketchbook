@echo off
:: Set Path to location of WinRAR
set PATH=%PATH%;F:\Program Files\WinRAR\

unrar x %1 %1\..
