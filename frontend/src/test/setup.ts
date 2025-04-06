import { expect, afterEach } from 'vitest';
import { cleanup } from '@testing-library/react';
import * as matchers from '@testing-library/jest-dom/matchers';

declare module 'vitest' {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  interface Assertion<T = any> {
    toBeInTheDocument(): T;
    toHaveValue(value: string | number | string[]): T;
    toHaveTextContent(text: string | RegExp): T;
  }
}

expect.extend(matchers);

afterEach(() => {
  cleanup();
}); 