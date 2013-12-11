class PairFrequencyCalculator
  A = 65570891
  C = 47024414
  G = 47016563
  T = 65668756
  TOTAL_PAIRS = (A + T + C + G).to_f

  def frequency_of_at
    ((A + T) / TOTAL_PAIRS * 100).round(3)
  end

  def frequency_of_cg
    ((C + G)/ TOTAL_PAIRS * 100).round(3)
  end
end

freq_calc = PairFrequencyCalculator.new
puts "AT: #{freq_calc.frequency_of_at}"
puts "CG: #{freq_calc.frequency_of_cg}"
