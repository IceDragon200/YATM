#!/usr/bin/env ruby
require 'json'

root = File.expand_path('../src/resources/assets/yatm/textures/blocks', File.dirname(__FILE__))
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

Dir.chdir root do
  ['BlockEnergyCell.Basic', 'BlockEnergyCell.Dense', 'BlockEnergyCell.Normal'].each do |subdir|
    Dir.chdir subdir do
      content = JSON.pretty_generate(data)
      Dir.glob("*.png").each do |filename|
        File.write filename + '.mcmeta', content
      end
    end
  end
end
