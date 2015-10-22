#!/usr/bin/env ruby
require 'json'

data = {
  "animation" => {
    "frametime" => 4,
    "frames" => [
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      1,
      2,
      3,
      2,
      1
    ]
  }
}

content = JSON.pretty_generate(data)
8.times { |i| File.write "Stage#{i}.png.mcmeta", content }
