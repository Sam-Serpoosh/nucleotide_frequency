class PairFrequencyCalculator
  AT = 16546616
  CG = 2252133
  TOTAL_PAIRS = (AT + CG).to_f

  def frequency_of_at
    (AT / TOTAL_PAIRS * 100).round(3)
  end

  def frequency_of_cg
    (CG / TOTAL_PAIRS * 100).round(3)
  end
end

freq_calc = PairFrequencyCalculator.new
puts "AT: #{freq_calc.frequency_of_at}"
puts "CG: #{freq_calc.frequency_of_cg}"
