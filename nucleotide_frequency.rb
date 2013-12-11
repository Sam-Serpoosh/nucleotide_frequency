class NucleotideFrequencyCalculator
  A = 65570891
  C = 47024414
  G = 47016563
  T = 65668756
  TOTAL_SEQUENCE = (A + C + G + T).to_f

  def frequency_of_adeninee
    (A / TOTAL_SEQUENCE * 100).round(3)
  end

  def frequency_of_thymine
    (T / TOTAL_SEQUENCE * 100).round(3)
  end

  def frequency_of_cytosine
    (C / TOTAL_SEQUENCE * 100).round(3)
  end

  def frequency_of_guanine
    (G / TOTAL_SEQUENCE * 100).round(3)
  end
end

freq_calc = NucleotideFrequencyCalculator.new
puts "A: #{freq_calc.frequency_of_adeninee}"
puts "C: #{freq_calc.frequency_of_cytosine}"
puts "T: #{freq_calc.frequency_of_thymine}"
puts "G: #{freq_calc.frequency_of_guanine}"
