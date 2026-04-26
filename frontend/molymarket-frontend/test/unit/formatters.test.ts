import { formatCurrency, capitalize } from '../../src/utils/formatters';

describe('Formatter Utils', () => {
  test('formatCurrency should format numbers correctly to USD', () => {
    // Note: Use regex because different environments might use different space characters (\u00a0 vs space)
    expect(formatCurrency(100)).toMatch(/\$100\.00/);
    expect(formatCurrency(0)).toMatch(/\$0\.00/);
  });

  test('capitalize should capitalize the first letter and lowercase the rest', () => {
    expect(capitalize('MOLY')).toBe('Moly');
    expect(capitalize('market')).toBe('Market');
    expect(capitalize('')).toBe('');
  });
});
