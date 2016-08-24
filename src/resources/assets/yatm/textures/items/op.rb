#!/usr/bin/env ruby
require 'active_support/core_ext/string'
require 'fileutils'

Dir.glob("*").each do |filename|
  dest_filename = filename.underscore.gsub(/\Ablock_/, '')
  p [filename, dest_filename]
  FileUtils.mv(filename, dest_filename) if filename != dest_filename
end

#Dir.glob("*").each do |basename|
#  if File.directory?(basename)
#    Dir.chdir basename do
#      Dir.glob("*.mcmeta").each do |filename|
#        dest_filename = filename.underscore.gsub(/\Ablock_/, '')
#        p [filename, dest_filename]
#        FileUtils.mv(filename, dest_filename) if filename != dest_filename
#      end
#    end
#  end
#end
